package com.example.mac_os.criminalintent.datebase;

public class crimeDbSchema {
    public static final class crimeTable {
        public static final String Name = "crime";

        public static final class Cols {
            public static final String Title = "title";
            public static final String Date = "date";
            public static final String Solved = "solved";
            public static final String UUID = "uuid";
            public static final String Suspect="suspect";
        }
    }
}
