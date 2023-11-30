package com.holub.database.jdbc;

import com.holub.database.jdbc.utils.SearchMode;
import com.holub.database.jdbc.utils.StringInspector;

import java.util.ArrayList;

public class QueryInfo {
    private static final String OPENING_MARKERS = "`'\"";
    private static final String CLOSING_MARKERS = "`'\"";
    private static final String OVERRIDING_MARKERS = "";

    private static final String INSERT_STATEMENT = "INSERT";
    private static final String REPLACE_STATEMENT = "REPLACE";

    private static final String VALUE_CLAUSE = "VALUE";
    private static final String AS_CLAUSE = "AS";
    private static final String[] ODKU_CLAUSE = new String[] { "ON", "DUPLICATE", "KEY", "UPDATE" };
    private static final String LAST_INSERT_ID_FUNC = "LAST_INSERT_ID";

    private QueryInfo baseQueryInfo = null;

    private String sql;
    private String encoding;
    private int queryLength = 0;
    private int queryStartPos = 0;
    private char statementFirstChar = Character.MIN_VALUE;
    private int batchCount = 1;
    private int numberOfPlaceholders = 0;
    private int numberOfQueries = 0;
    private boolean containsOnDuplicateKeyUpdate = false;
    private boolean isRewritableWithMultiValuesClause = false;
    private int valuesClauseLength = -1;
    private ArrayList<Integer> valuesEndpoints = new ArrayList<>();
    private byte[][] staticSqlParts = null;

    /**
     * Constructs a {@link QueryInfo} object for the given query or multi-query. The parsed result of this query allows to determine the location of the
     * placeholders, the query static parts and whether this query can be rewritten as a multi-values clause query.
     *
     * @param sql
     *            the query SQL string to parse and analyze
     */
    public QueryInfo(String sql) {
        if (sql == null) {
            throw new IllegalArgumentException("SQL string cannot be null");
        }

        this.baseQueryInfo = this;

        this.sql = sql;
        this.encoding = encoding;

        boolean noBackslashEscapes = false;
        boolean rewriteBatchedStatements = false;
        boolean dontCheckOnDuplicateKeyUpdateInSQL = false;
        this.queryLength = this.sql.length();

        StringInspector strInspector = new StringInspector(this.sql, OPENING_MARKERS, CLOSING_MARKERS, OVERRIDING_MARKERS,
                noBackslashEscapes ? SearchMode.__MRK_COM_MYM_HNT_WS : SearchMode.__BSE_MRK_COM_MYM_HNT_WS);

        // Skip comments at the beginning of queries.
        this.queryStartPos = strInspector.indexOfNextAlphanumericChar();
        if (this.queryStartPos == -1) {
            this.queryStartPos = this.queryLength;
        } else {
            this.numberOfQueries = 1;
            this.statementFirstChar = Character.toUpperCase(strInspector.getChar());
        }

        // Only INSERT and REPLACE statements support multi-values clause rewriting.
        boolean isInsert = strInspector.matchesIgnoreCase(INSERT_STATEMENT) != -1;
        if (isInsert) {
            strInspector.incrementPosition(INSERT_STATEMENT.length()); // Advance to the end of "INSERT".
        }
        boolean isReplace = !isInsert && strInspector.matchesIgnoreCase(REPLACE_STATEMENT) != -1;
        if (isReplace) {
            strInspector.incrementPosition(REPLACE_STATEMENT.length()); // Advance to the end of "REPLACE".
        }

        // Check if the statement has potential to be rewritten as a multi-values clause statement, i.e., if it is an INSERT or REPLACE statement and
        // 'rewriteBatchedStatements' is enabled.
        boolean rewritableAsMultiValues = (isInsert || isReplace) && rewriteBatchedStatements;

        // Check if should look for ON DUPLICATE KEY UPDATE CLAUSE, i.e., if it is an INSERT statement and 'dontCheckOnDuplicateKeyUpdateInSQL' is disabled.
        // 'rewriteBatchedStatements=true' cancels any value specified in 'dontCheckOnDuplicateKeyUpdateInSQL'.
        boolean lookForOnDuplicateKeyUpdate = isInsert && (!dontCheckOnDuplicateKeyUpdateInSQL || rewriteBatchedStatements);

        // Scan placeholders.
        int generalEndpointStart = 0;
        int valuesEndpointStart = 0;
        int valuesClauseBegin = -1;
        boolean valuesClauseBeginFound = false;
        int valuesClauseEnd = -1;
        boolean valuesClauseEndFound = false;
        boolean withinValuesClause = false;
        boolean valueStrMayBeTableName = true;
        int parensLevel = 0;
        int matchEnd = -1;
        int lastPos = -1;
        char lastChar = 0;

        // Endpoints for the satement's static sections (parts around placeholders).
        ArrayList<Integer> staticEndpoints = new ArrayList<>();

        while (strInspector.indexOfNextChar() != -1) {
            int currPos = strInspector.getPosition();
            char currChar = strInspector.getChar();

            if (currChar == '?') { // Process placeholder.
                valueStrMayBeTableName = false; // At this point a string "VALUE" cannot be a table name.

                this.numberOfPlaceholders++;
                int endpointEnd = strInspector.getPosition();
                staticEndpoints.add(generalEndpointStart);
                staticEndpoints.add(endpointEnd);
                strInspector.incrementPosition();
                generalEndpointStart = strInspector.getPosition(); // Next section starts after the placeholder.

                if (rewritableAsMultiValues) {
                    if (!valuesClauseBeginFound) { // There's a placeholder before the VALUES clause.
                        rewritableAsMultiValues = false;
                    } else if (valuesClauseEndFound) { // There's a placeholder after the end of the VALUES clause.
                        rewritableAsMultiValues = false;
                    } else if (withinValuesClause) {
                        this.valuesEndpoints.add(valuesEndpointStart);
                        this.valuesEndpoints.add(endpointEnd);
                        valuesEndpointStart = generalEndpointStart;
                    }
                }

            } else if (currChar == ';') { // Multi-query SQL.
                valueStrMayBeTableName = false; // At this point a string "VALUE" cannot be a table name.

                strInspector.incrementPosition();
                if (strInspector.indexOfNextNonWsChar() != -1) {
                    this.numberOfQueries++;

                    if (rewritableAsMultiValues) {
                        rewritableAsMultiValues = false;
                        valuesClauseBeginFound = false;
                        valuesClauseBegin = -1;
                        valuesClauseEndFound = false;
                        valuesClauseEnd = -1;
                        withinValuesClause = false;
                        parensLevel = 0;
                    }

                    // Check if continue looking for ON DUPLICATE KEY UPDATE.
                    if (dontCheckOnDuplicateKeyUpdateInSQL || this.containsOnDuplicateKeyUpdate) {
                        lookForOnDuplicateKeyUpdate = false;
                    } else {
                        isInsert = strInspector.matchesIgnoreCase(INSERT_STATEMENT) != -1;
                        if (isInsert) {
                            strInspector.incrementPosition(INSERT_STATEMENT.length() - 1); // Advance to the end of "INSERT" and capture last character.
                            currPos = strInspector.getPosition();
                            currChar = strInspector.getChar();
                            strInspector.incrementPosition();
                        }
                        lookForOnDuplicateKeyUpdate = isInsert;
                    }
                }

            } else {
                if (rewritableAsMultiValues) {
                    if ((!valuesClauseBeginFound || valueStrMayBeTableName) && strInspector.matchesIgnoreCase(VALUE_CLAUSE) != -1) { // VALUE(S) clause found.
                        boolean leftBound = currPos > lastPos + 1 || lastChar == ')'; // ')' would mark the ending of the columns list.

                        strInspector.incrementPosition(VALUE_CLAUSE.length() - 1); // Advance to the end of "VALUE" and capture last character.
                        currPos = strInspector.getPosition();
                        currChar = strInspector.getChar();
                        strInspector.incrementPosition();
                        boolean matchedValues = false;
                        if (strInspector.matchesIgnoreCase("S") != -1) { // Check for the "S" in "VALUE(S)" and advance 1 more character if needed.
                            currPos = strInspector.getPosition();
                            currChar = strInspector.getChar();
                            strInspector.incrementPosition();
                            matchedValues = true;
                        }

                        int endPos = strInspector.getPosition();
                        int nextPos = strInspector.indexOfNextChar(); // Position on the first meaningful character after VALUE(S).
                        boolean rightBound = nextPos > endPos || strInspector.getChar() == '('; // '(' would mark the beginning of the VALUE(S) list.

                        if (leftBound && rightBound) { // VALUE(S) keyword must not be part of another string, such as a table or column name.
                            if (matchedValues) {
                                valueStrMayBeTableName = false; // At this point a string "VALUE" cannot be a table name.
                            }
                            if (matchedValues && this.containsOnDuplicateKeyUpdate) { // VALUES after ODKU is a function, not a clause.
                                rewritableAsMultiValues = false;
                            } else {
                                withinValuesClause = true;
                                valuesClauseBegin = strInspector.getPosition();
                                valuesClauseBeginFound = true;
                                valuesEndpointStart = valuesClauseBegin;
                            }
                        }

                    } else if (withinValuesClause && currChar == '(') {
                        parensLevel++;
                        strInspector.incrementPosition();

                    } else if (withinValuesClause && currChar == ')') {
                        parensLevel--;
                        if (parensLevel < 0) {
                            parensLevel = 0; // Keep going, not checking for syntax validity.
                        }
                        strInspector.incrementPosition();
                        valuesClauseEnd = strInspector.getPosition(); // It may not be the end of the VALUES clause yet but save it for later.

                    } else if (withinValuesClause && parensLevel == 0 && isInsert && strInspector.matchesIgnoreCase(AS_CLAUSE) != -1) { // End of VALUES clause.
                        valueStrMayBeTableName = false; // At this point a string "VALUE" cannot be a table name.

                        if (valuesClauseEnd == -1) {
                            valuesClauseEnd = strInspector.getPosition();
                        }
                        valuesClauseEndFound = true;
                        withinValuesClause = false;
                        strInspector.incrementPosition(AS_CLAUSE.length() - 1); // Advance to the end of "AS" and capture last character.
                        currPos = strInspector.getPosition();
                        currChar = strInspector.getChar();
                        strInspector.incrementPosition();

                        this.valuesEndpoints.add(valuesEndpointStart);
                        this.valuesEndpoints.add(valuesClauseEnd);

                    } else if (withinValuesClause && parensLevel == 0 && isInsert //
                            && (matchEnd = strInspector.matchesIgnoreCase(ODKU_CLAUSE)) != -1) { // End of VALUES clause.
                        valueStrMayBeTableName = false; // At this point a string "VALUE" cannot be a table name.

                        if (valuesClauseEnd == -1) {
                            valuesClauseEnd = strInspector.getPosition();
                        }
                        valuesClauseEndFound = true;
                        withinValuesClause = false;
                        strInspector.incrementPosition(matchEnd - strInspector.getPosition() - 1); // Advance to the end of "ODKU" and capture last character.
                        currPos = strInspector.getPosition();
                        currChar = strInspector.getChar();
                        strInspector.incrementPosition();

                        this.valuesEndpoints.add(valuesEndpointStart);
                        this.valuesEndpoints.add(valuesClauseEnd);

                        this.containsOnDuplicateKeyUpdate = true;
                        lookForOnDuplicateKeyUpdate = false;

                    } else if (strInspector.matchesIgnoreCase(LAST_INSERT_ID_FUNC) != -1) { // Can't rewrite as multi-values if LAST_INSERT_ID function is used.
                        rewritableAsMultiValues = false;
                        strInspector.incrementPosition(LAST_INSERT_ID_FUNC.length() - 1); // Advance to the end of "LAST_INSERT_ID" and capture last character.
                        currPos = strInspector.getPosition();
                        currChar = strInspector.getChar();
                        strInspector.incrementPosition();
                    }
                }

                if (lookForOnDuplicateKeyUpdate && currPos == strInspector.getPosition() && (matchEnd = strInspector.matchesIgnoreCase(ODKU_CLAUSE)) != -1) {
                    strInspector.incrementPosition(matchEnd - strInspector.getPosition() - 1); // Advance to the end of "ODKU" and capture last character.
                    currPos = strInspector.getPosition();
                    currChar = strInspector.getChar();
                    strInspector.incrementPosition();

                    this.containsOnDuplicateKeyUpdate = true;
                    lookForOnDuplicateKeyUpdate = false;
                }

                if (currPos == strInspector.getPosition()) {
                    strInspector.incrementPosition();
                }
            }

            lastPos = currPos;
            lastChar = currChar;
        }
        staticEndpoints.add(generalEndpointStart);
        staticEndpoints.add(this.queryLength);
        if (rewritableAsMultiValues) {
            if (!valuesClauseEndFound) {
                if (valuesClauseEnd == -1) {
                    valuesClauseEnd = this.queryLength;
                }
                valuesClauseEndFound = true;
                withinValuesClause = false;

                this.valuesEndpoints.add(valuesEndpointStart);
                this.valuesEndpoints.add(valuesClauseEnd);
            }

            if (valuesClauseBeginFound && valuesClauseEndFound) {
                this.valuesClauseLength = valuesClauseEnd - valuesClauseBegin;
            } else {
                rewritableAsMultiValues = false;
            }
        } else {
            this.valuesEndpoints.clear();
        }
        this.isRewritableWithMultiValuesClause = rewritableAsMultiValues;

        this.staticSqlParts = new byte[this.numberOfPlaceholders + 1][];
        for (int i = 0, j = 0; i <= this.numberOfPlaceholders; i++) {
            int begin = staticEndpoints.get(j++);
            int end = staticEndpoints.get(j++);
            int length = end - begin;
            //this.staticSqlParts[i] = StringUtils.getBytes(this.sql, begin, length, this.encoding);
        }
    }
}
