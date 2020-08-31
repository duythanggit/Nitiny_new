package vn.chapp.vn24h.utils.rx;

public class Buser {
    public static final String KEY_CHANGE_PAGE_JOBS = "KEY_CHANGE_PAGE_JOBS";


    private String key;
    private Object values;

    public Buser(String key, Object values) {
        this.key = key;
        this.values = values;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValues() {
        return values;
    }

    public void setValues(Object values) {
        this.values = values;
    }
}
