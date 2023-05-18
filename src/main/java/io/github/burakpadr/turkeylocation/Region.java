package io.github.burakpadr.turkeylocation;

public enum Region {
    
    AKDENIZ("akdeniz"),
    DOGU_ANADOLU("doğu anadolu"),
    EGE("ege"),
    GUNEYDOGU_ANADOLU("güneydoğu anadolu"),
    IC_ANADOLU("iç anadolu"),
    MARMARA("marmara"),
    KARADENIZ("karadeniz");

    private String regionName;

    private Region(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionName() {
        return regionName;
    }
}
