package com.jqh.libpermissionhelper;

public interface PermissionProxy<T> {
    void grant(int requestCode, T source, String[] permissions);

    void denied(int requestCode, T source, String [] permissions);

    boolean rational(int requestCode, T source, String [] permissions);
}
