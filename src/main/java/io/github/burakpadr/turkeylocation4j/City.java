package io.github.burakpadr.turkeylocation4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class represents the city
 * 
 * @author burakpadr
 */

public class City {

    private String name;
    private Integer plateNumber;
    private Double latitude;
    private Double longitude;
    private Integer metropolitianMunicipalitySince;
    private String region;
    private Boolean hasSeaAccess;
    private Integer population;
    private Integer areaKm2;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(Integer plateNumber) {
        this.plateNumber = plateNumber;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getMetropolitianMunicipalitySince() {
        return metropolitianMunicipalitySince;
    }

    public void setMetropolitianMunicipalitySince(Integer metropolitianMunicipalitySince) {
        this.metropolitianMunicipalitySince = metropolitianMunicipalitySince;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Boolean getHasSeaAccess() {
        return hasSeaAccess;
    }

    public void setHasSeaAccess(Boolean hasSeaAccess) {
        this.hasSeaAccess = hasSeaAccess;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public Integer getAreaKm2() {
        return areaKm2;
    }

    public void setAreaKm2(Integer areaKm2) {
        this.areaKm2 = areaKm2;
    }

    /**
     * This function creates a city object by the name e.g: ('istanbul',
     * 'ankara', etc.)
     * 
     * @param name
     * @return the city object
     * @since 1.0.0
     */

    public static Optional<City> fromName(String name) {
        List<City> city = CityParser.getInstance().parse(Optional.of("name"), Optional.ofNullable(name));

        return city.size() > 0 ? Optional.of(city.get(0)) : Optional.empty();
    }

    /**
     * This function creates a city object by the plate number
     * 
     * @param plateNumber
     * @return the city object
     * @since 1.0.0
     */

    public static Optional<City> fromPlateNumber(Integer plateNumber) {
        List<City> city = CityParser.getInstance().parse(Optional.of("plateNumber"), Optional.ofNullable(plateNumber));

        return city.size() > 0 ? Optional.of(city.get(0)) : Optional.empty();
    }

    /**
     * This function creates objects of cities within the region.
     * 
     * @param region
     * @return the city objects
     * @since 1.0.0
     */

    public static List<City> fromRegion(Region region) {
        return CityParser.getInstance().parse(Optional.of("region"), Optional.ofNullable(region.getRegionName()));
    }

    /**
     * This function creates the objects of cities that have sea access.
     * 
     * @param hasSeaAccess
     * @return the city objects
     * @since 1.0.0
     */

    public static List<City> fromHasSeaAccess(Boolean hasSeaAccess) {
        return CityParser.getInstance().parse(Optional.of("hasSeaAccess"), Optional.ofNullable(hasSeaAccess));
    }

    /**
     * This function creates the objects of cities without any filter
     * 
     * @return the city objects
     * @since 1.0.0
     */

    public static List<City> fromNoFilter() {
        return CityParser.getInstance().parse(Optional.empty(), Optional.empty());
    }

    @Override
    public String toString() {
        return "{\n name: " + name + ",\n latitude: " + latitude + ",\n longitude: " + longitude +
                ",\n metropolitianMunicipalitySince: " + metropolitianMunicipalitySince + ",\n region: " + region
                + ",\n hasSeaAccess: " + hasSeaAccess + ",\n population: " + population + ",\n areaKm2: " + areaKm2
                + "\n}";
    }

    private static class CityParser {

        private static CityParser cityParser;
        private static final String CITY_FILE_PATH = "data/cities.yaml";

        private Map<String, String> cityParametersMap;

        private CityParser() {
            cityParametersMap = new HashMap<>();

            cityParametersMap.put("name", "name");
            cityParametersMap.put("plateNumber", "plate_number");
            cityParametersMap.put("latitude", "latitude");
            cityParametersMap.put("longitude", "longitude");
            cityParametersMap.put("metropolitianMunicipalitySince", "metropolitian_municipality_since");
            cityParametersMap.put("region", "region");
            cityParametersMap.put("hasSeaAccess", "has_sea_access");
            cityParametersMap.put("population", "population");
            cityParametersMap.put("areaKm2", "area");
        }

        public static CityParser getInstance() {
            if (Objects.isNull(cityParser))
                return new CityParser();
            else
                return cityParser;
        }

        @SuppressWarnings("unchecked")
        public List<City> parse(Optional<String> nameOfMemberVariableOptional, Optional<Object> queryKeyOptional) {
            List<Map<String, Object>> content = (List<Map<String, Object>>) YamlUtils
                    .yamlToJson(CITY_FILE_PATH);

            if (nameOfMemberVariableOptional.isPresent())
                return content.stream()
                        .filter(
                                cityAsMap -> {
                                    String queryKeyString = queryKeyOptional.get() instanceof String
                                            ? (String) queryKeyOptional.get()
                                            : null;

                                    String key = cityParametersMap.get(nameOfMemberVariableOptional.get());

                                    return Objects.nonNull(queryKeyString)
                                            ? StringUtils.clearTurkishChars(((String) cityAsMap.get(key))).toLowerCase()
                                                    .equals(StringUtils.clearTurkishChars(queryKeyString).toLowerCase())
                                            : cityAsMap.get(key).equals(queryKeyOptional.get());
                                })
                        .map(CityParser::convertMapToCity)
                        .collect(Collectors.toList());
            else
                return content.stream().parallel()
                        .map(CityParser::convertMapToCity)
                        .collect(Collectors.toList());
        }

        private static City convertMapToCity(Map<String, Object> map) {
            City city = new City();

            city.setName(((String) map.get("name")));
            city.setPlateNumber(((Integer) map.get("plate_number")));
            city.setLatitude(((Double) map.get("latitude")));
            city.setLongitude(((Double) map.get("longitude")));
            city.setMetropolitianMunicipalitySince(((Integer) map
                    .get("metropolitian_municipality_since")));
            city.setRegion(((String) map.get("region")));
            city.setHasSeaAccess(((Boolean) map.get("has_sea_access")));
            city.setPopulation(((Integer) map.get("population")));
            city.setAreaKm2(((Integer) map.get("area")));

            return city;
        }
    }
}
