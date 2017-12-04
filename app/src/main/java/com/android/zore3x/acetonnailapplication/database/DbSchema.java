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
}
