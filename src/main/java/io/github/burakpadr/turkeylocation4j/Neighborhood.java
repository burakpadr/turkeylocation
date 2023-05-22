package io.github.burakpadr.turkeylocation4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class represents the neighborhood
 * 
 * @author burakpadr
 */
public class Neighborhood {

    private String name;
    private Integer postCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPostCode() {
        return postCode;
    }

    public void setPostCode(Integer postCode) {
        this.postCode = postCode;
    }

    /**
     * This function creates objects of neighborhoods within the city and district.
     * 
     * @param cityName
     * @param districtName
     * @return the neighborhood objects
     * @since 1.0.0
     */

    public static List<Neighborhood> fromCityNameAndDistrictName(String cityName, String districtName) {
        return NeighborhoodParser.parse(cityName, districtName);
    }

    private static class NeighborhoodParser {

        private static final String DISTRICTS_DIR_PATH = "data/districts";

        @SuppressWarnings("unchecked")
        public static List<Neighborhood> parse(String cityName, String districtName) {
            Map<String, Object> content = (Map<String, Object>) YamlUtils
                    .yamlToJson(DISTRICTS_DIR_PATH.concat("/")
                            .concat(StringUtils.clearTurkishChars(cityName).toLowerCase()).concat(".yaml"));

            List<Neighborhood> neigborhoods = new ArrayList<>();

            content.entrySet().stream().forEach(entry -> {
                String districtNameOfEntry = StringUtils.clearTurkishChars(entry.getKey()).toLowerCase();

                if (StringUtils.clearTurkishChars(districtName).toLowerCase().equals(districtNameOfEntry)) {
                    ((Map<String, Object>) entry.getValue()).entrySet().stream().forEach(entry2 -> {
                        List<String> neighborhoodNames = (List<String>) ((Map<String, Object>) entry2.getValue())
                                .get("neighborhoods");

                        Integer postCode = (Integer) ((Map<String, Object>) entry2.getValue()).get("postcode");

                        neighborhoodNames.stream().forEach(neighborhoodName -> {
                            Neighborhood neighborhood = new Neighborhood();

                            neighborhood.setName(neighborhoodName);
                            neighborhood.setPostCode(postCode);

                            neigborhoods.add(neighborhood);
                        });
                    });
                }
            });

            return neigborhoods;
        }
    }
}
