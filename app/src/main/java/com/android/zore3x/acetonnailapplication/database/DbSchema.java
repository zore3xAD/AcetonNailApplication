package com.android.zore3x.acetonnailapplication.database;

/**
 * Created by DobrogorskiyAA on 28.11.2017.
 */

public class DbSchema {

    public static final class ClientTable {
        public static final String NAME = "ClientTable";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String NAME = "name";
            public static final String SURNAME = "surname";
            public static final String PHONE = "phone";
        }
    }

    public static final class MasterTable {
        public static final String NAME = "MasterTable";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String NAME = "name";
            public static final String SURNAME = "surname";
            public static final String PHONE = "phone";
        }
    }

    public static final class ProcedureTable {
        public static final String NAME = "ProcedureTable";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
        }
    }

    public static final class MasterTypeTable {
        public static final String NAME = "MasterTypeTable";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String UUID_MASTER = "uuid_master";
            public static final String UUID_PROCEDURE = "uuid_procedure";
        }
    }

    public static final class VisitTable {
        public static final String NAME = "VisitTable";

        public static final class Cols {
            public static final String UUID = "UUID";
            public static final String UUID_CLIENT = "UUID_CLIENT";
            public static final String UUID_MASTER = "UUID_MASTER";
            public static final String UUID_PROCEDURE = "UUID_PROCEDURE";
            public static final String DATE = "DATE";
        }
    }

    public static final class VisitStatusTable {
        public static final String NAME = "VisitStatusTable";

        public static final String STATUS_OK = "1";
        public static final String STATUS_WAIT = "2";
        public static final String STATUS_CANCEL = "3";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String UUID_VISIT = "uuid_visit";
            public static final String STATUS = "status";
        }
    }
}
