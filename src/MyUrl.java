public class MyUrl {

    String url;
    Integer inLinks;

    public MyUrl(String url, int inLinks) {
        this.url = url;
        this.inLinks = inLinks;
    }

    public String getUrl() {
        return url;
    }

    public Integer getInLinks() {
        return inLinks;
    }

    public void setInLinks(Integer inLinks) {
        this.inLinks = inLinks;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (url == null ? 0 : url.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MyUrl other = (MyUrl) obj;
        if (url == null) {
            if (other.url != null)
                return false;
        } else if (!url.equals(other.url))
            return false;
        return true;
    }

}
