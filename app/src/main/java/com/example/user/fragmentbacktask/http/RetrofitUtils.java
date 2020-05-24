package com.example.user.fragmentbacktask.http;

public enum RetrofitUtils {
    INSTANCE;
    private static CompanyAPI mCompanyAPI;

   public static CompanyAPI getCompanyApi() {
       return getAPI(mCompanyAPI, CompanyAPI.class);
   }

    public static <T> T getAPI(T t, Class<T> tClass) {
        return t == null ? KrRetrofit.INSTANCE.getRetrofit()
                .create(tClass) : t;
    }

}
