package com.base.app.core.code;

import com.fasterxml.jackson.annotation.JsonValue;

public class Types {
    public Types() {
    }

    public static enum DataStatus {
        CREATED,
        MODIFIED,
        DELETED,
        ORIGIN;

        private DataStatus() {
        }
    }

    public static enum UserStatus implements LabelEnum {
        ACCOUNT_LOCK("ACCOUNT_LOCK"),
        NORMAL("NORMAL");

        private final String label;

        private UserStatus(String label) {
            this.label = label;
        }

        @JsonValue
        public String getLabel() {
            return this.label;
        }
    }

    public static enum Deleted implements LabelEnum {
        YES("Y"),
        NO("N");

        private final String label;

        private Deleted(String label) {
            this.label = label;
        }

        @JsonValue
        public String getLabel() {
            return this.label;
        }

        public static Types.Deleted get(String delYn) {
            Types.Deleted[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                Types.Deleted deleted = var1[var3];
                if (deleted.getLabel().equals(delYn)) {
                    return deleted;
                }
            }

            return null;
        }
    }

    public static enum Used implements LabelEnum {
        YES("Y"),
        NO("N");

        private final String label;

        private Used(String label) {
            this.label = label;
        }

        @JsonValue
        public String getLabel() {
            return this.label;
        }

        public static Types.Used get(String useYn) {
            Types.Used[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                Types.Used used = var1[var3];
                if (used.getLabel().equals(useYn)) {
                    return used;
                }
            }

            return null;
        }
    }

    public static enum SecurtiyMethod implements LabelEnum {
        METHOD("method"),
        POINTCUT("pointcut");

        private final String label;

        private SecurtiyMethod(String label) {
            this.label = label;
        }

        @JsonValue
        public String getLabel() {
            return this.label;
        }
    }
}
