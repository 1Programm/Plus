package com.programm.projects.plus.resource.api;

import com.programm.projects.plus.core.resource.Resource;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ValueResource implements Resource {

    public static ValueResource OfUndefined(Object value){
        Class<?> cls = value.getClass();

        if(cls == String.class){
            String val = (String)value;

            //Boolean
            if(val.equalsIgnoreCase("true")){
                return Of(true);
            }
            else if(val.equalsIgnoreCase("false")){
                return Of(false);
            }

            //Int
            try {
                int iVal = Integer.parseInt(val);
                return Of(iVal);
            } catch(NumberFormatException ignored){ }

            //Long
            try {
                long lVal = Long.parseLong(val);
                return Of(lVal);
            } catch(NumberFormatException ignored){ }

            //Float
            try {
                float fVal = Float.parseFloat(val);
                return Of(fVal);
            } catch(NumberFormatException ignored){ }

            //Double
            try {
                double dVal = Double.parseDouble(val);
                return Of(dVal);
            } catch(NumberFormatException ignored){ }

            return Of((String)value);
        }
        else if(cls == Boolean.class){
            return Of((boolean)value);
        }
        else if(cls == Integer.class){
            return Of((int)value);
        }
        else if(cls == Long.class){
            return Of((long)value);
        }
        else if(cls == Float.class){
            return Of((float)value);
        }
        else if(cls == Double.class){
            return Of((double)value);
        }

        throw new IllegalArgumentException("Type " + cls + " is not a primitive resource value!");
    }

    public static ValueResource Of(String value){
        return new ValueResource(String.class, value, null, null, null, null, null);
    }

    public static ValueResource Of(boolean value){
        return new ValueResource(Boolean.class, null, value, null, null, null, null);
    }

    public static ValueResource Of(int value){
        return new ValueResource(Integer.class, null, null, value, null, null, null);
    }

    public static ValueResource Of(long value){
        return new ValueResource(Long.class, null, null, null, value, null, null);
    }

    public static ValueResource Of(float value){
        return new ValueResource(Float.class, null, null, null, null, value, null);
    }

    public static ValueResource Of(double value){
        return new ValueResource(Double.class, null, null, null, null, null, value);
    }

    private final Class<?> origin;

    private String stringValue;
    private Boolean booleanValue;
    private Integer intValue;
    private Long longValue;
    private Float floatValue;
    private Double doubleValue;

    @Override
    public String asString() {
        if(stringValue == null){
            String value = null;

            if(origin == String.class){
                value = null;
            }
            else if(origin == Boolean.class){
                value = "" + asBoolean();
            }
            else if(origin == Integer.class){
                value = "" + asInt();
            }
            else if(origin == Long.class){
                value = "" + asLong();
            }
            else if(origin == Float.class){
                value = "" + asFloat();
            }
            else if(origin == Double.class){
                value = "" + asDouble();
            }

            this.stringValue = value;
        }

        return stringValue;
    }

    @Override
    public Boolean asBoolean() {
        if(booleanValue == null){
            Boolean value = null;

            if(origin == String.class){
                value = Boolean.parseBoolean(asString());
            }
            else if(origin == Boolean.class){
                value = null;
            }
            else if(origin == Integer.class){
                value = (asInt() != 0);
            }
            else if(origin == Long.class){
                value = (asLong() != 0);
            }
            else if(origin == Float.class){
                value = (asFloat() != 0);
            }
            else if(origin == Double.class){
                value = (asDouble() != 0);
            }

            this.booleanValue = value;
        }

        return booleanValue;
    }

    @Override
    public Integer asInt() {
        if(intValue == null){
            Integer value = null;

            if(origin == String.class){
                value = Integer.parseInt(asString());
            }
            else if(origin == Boolean.class){
                value = asBoolean() ? 1 : 0;
            }
            else if(origin == Integer.class){
                value = null;
            }
            else if(origin == Long.class){
                value = (int)asLong(0);
            }
            else if(origin == Float.class){
                value = (int)asFloat(0);
            }
            else if(origin == Double.class){
                value = (int)asDouble(0);
            }

            this.intValue = value;
        }

        return intValue;
    }

    @Override
    public Long asLong() {
        if(longValue == null){
            Long value = null;

            if(origin == String.class){
                value = Long.parseLong(asString());
            }
            else if(origin == Boolean.class){
                value = asBoolean() ? 1L : 0L;
            }
            else if(origin == Integer.class){
                value = (long)asInt(0);
            }
            else if(origin == Long.class){
                value = null;
            }
            else if(origin == Float.class){
                value = (long)asFloat(0);
            }
            else if(origin == Double.class){
                value = (long)asDouble(0);
            }

            this.longValue = value;
        }

        return longValue;
    }

    @Override
    public Float asFloat() {
        if(floatValue == null){
            Float value = null;

            if(origin == String.class){
                value = Float.parseFloat(asString());
            }
            else if(origin == Boolean.class){
                value = asBoolean() ? 1f : 0f;
            }
            else if(origin == Integer.class){
                value = (float)asInt(0);
            }
            else if(origin == Long.class){
                value = (float)asLong(0);
            }
            else if(origin == Float.class){
                value = null;
            }
            else if(origin == Double.class){
                value = (float)asDouble(0);
            }

            this.floatValue = value;
        }

        return floatValue;
    }

    @Override
    public Double asDouble() {
        if(doubleValue == null){
            Double value = null;

            if(origin == String.class){
                value = Double.parseDouble(asString());
            }
            else if(origin == Boolean.class){
                value = asBoolean() ? 1d : 0d;
            }
            else if(origin == Integer.class){
                value = (double)asInt(0);
            }
            else if(origin == Long.class){
                value = (double)asLong(0);
            }
            else if(origin == Float.class){
                value = (double)asFloat(0);
            }
            else if(origin == Double.class){
                value = null;
            }

            this.doubleValue = value;
        }

        return doubleValue;
    }

    @Override
    public Resource get(String name) {
        return null;
    }

    @Override
    public String toString() {
        return asString();
    }
}
