package cn.zzuzl;

public class QueryServiceImpl implements QueryService {

    @Override
    public String query() {
        System.out.println("query");
        return "query";
    }
}
