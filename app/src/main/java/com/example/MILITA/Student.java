package com.example.MILITA;

public class Student {
        private String name;
        private String ID;
        private String classStudent;

        public Student(String name, String ID, String classStudent) {
            this.name = name;
            this.ID = ID;
            this.classStudent = classStudent;
        }

        public String getName() { return name; }
        public String getId() { return ID; }
        public String getClassStudent() { return classStudent; }



}
