//package com.holub.database.jdbc.queryBinding;
//import com.holub.database.jdbc.queryBinding.BindValue;
//
//import java.math.BigInteger;
//
//
//public class ValueEncoder {
//    public String getString(BindValue binding) {
//        String x = (String) binding.getValue();
//        switch (binding.getMysqlType()) {
//            case NULL:
//                return "null";
//            case BOOLEAN:
//            case BIT:
//                Boolean b = null;
//                if ("true".equalsIgnoreCase(x) || "Y".equalsIgnoreCase(x)) {
//                    b = true;
//                } else if ("false".equalsIgnoreCase(x) || "N".equalsIgnoreCase(x)) {
//                    b = false;
//                } else if (x.matches("-?\\d+\\.?\\d*")) {
//                    b = !x.matches("-?[0]+[.]*[0]*");
//                } else {
//                    throw new IllegalStateException(new String("\"PreparedStatement.66\""));
//                }
//                return String.valueOf(b ? 1 : 0);
//            case TINYINT:
//            case TINYINT_UNSIGNED:
//            case SMALLINT:
//            case SMALLINT_UNSIGNED:
//            case MEDIUMINT:
//            case MEDIUMINT_UNSIGNED:
//            case INT:
//                return String.valueOf(Integer.parseInt(x));
//            case INT_UNSIGNED:
//            case BIGINT:
//                return String.valueOf(Long.parseLong(x));
//            case BIGINT_UNSIGNED:
//                return String.valueOf(new BigInteger(x).longValue());
//            case FLOAT:
////            case FLOAT_UNSIGNED:
////                return StringUtils.fixDecimalExponent(Float.toString(Float.parseFloat(x)));
//            case DOUBLE:
////            case DOUBLE_UNSIGNED:
////                return StringUtils.fixDecimalExponent(Double.toString(Double.parseDouble(x)));
//            case DECIMAL:
////            case DECIMAL_UNSIGNED:
////                return getScaled(new BigDecimal(x), binding.getScaleOrLength()).toPlainString();
//            case CHAR:
//            case ENUM:
//            case SET:
//            case VARCHAR:
//            case TINYTEXT:
//            case TEXT:
//            case MEDIUMTEXT:
//            case LONGTEXT:
//            case JSON:
//            case BINARY:
//            case GEOMETRY:
//            case VARBINARY:
//            case TINYBLOB:
//            case BLOB:
//            case MEDIUMBLOB:
//            case LONGBLOB:
//                StringBuilder sb = new StringBuilder("'");
//                sb.append(x);
//                sb.append("'");
//                return sb.toString();
//        }
//        throw new IllegalArgumentException(new String ("PreparedStatement.67"));
//    }
//}
