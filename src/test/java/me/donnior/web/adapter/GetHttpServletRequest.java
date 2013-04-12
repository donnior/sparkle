package me.donnior.web.adapter;

public class GetHttpServletRequest extends HttpServletRequestAdapter{

    private String url;

    public GetHttpServletRequest(String url) {
        this.url = url;
    }
    
    @Override
    public String getPathInfo() {
        return url;
    }
    
    @Override
    public String getMethod() {
        return "get";
    }
}
