# turkeylocation4j

Turkeylocation4j is a java module where you can get Turkey's city, district and neighborhood information.

The data used in this module is taken from the [Turkish Cities](https://github.com/sarslanoglu/turkish_cities) project. I would like to thank Semih Arslanoğlu for allowing this data to be used.

# Documentation

- **City API**

	 - **Find city from name**
				 
		 ```rb
		import io.github.burakpadr.turkeylocation4j.City;
		
		City.fromName("İstanbul").orElseThrow(NoSuchElementException::new);   # => {name: İstanbul, latitude: 41.0082, ...}
		City.fromName("Istanbul").orElseThrow(NoSuchElementException::new);   # => {name: İstanbul, latitude: 41.0082, ...}
		City.fromName("istanbul").orElseThrow(NoSuchElementException::new);   # => {name: İstanbul, latitude: 41.0082, ...}
		City.fromName("ankara").orElseThrow(NoSuchElementException::new);   # => {name: Ankara, latitude: 39.9334, ...}
		```

	- **Find city from plate number** 

		 ```rb
		import io.github.burakpadr.turkeylocation4j.City;

		City.fromPlateNumber(01).orElseThrow(NoSuchElementException::new); => # => {name: Adana, latitude: 35.3308, ...}
		City.fromPlateNumber(57).orElseThrow(NoSuchElementException::new); => # => {name: Sinop, latitude: 42.028, ...}
		```

	- **Find cities from region**

		```rb
		import io.github.burakpadr.turkeylocation4j.City;
		import io.github.burakpadr.turkeylocation4j.Region;
		
		City.fromRegion(Region.EGE); # => [{name: Afyon, latitude: 38.7595, ...}, ...]
		City.fromRegion(Region.KARADENIZ); # => [{name: Amasya, latitude: 40.6565, ...}, ...]
		```

	-	**Find cities that have sea access**
		
		```rb
		import io.github.burakpadr.turkeylocation4j.City;

		City.fromHasSeaAccess(true); # => [{name: Adana, latitude: 35.3308, ...}, ...]
		City.fromHasSeaAccess(false); # => [{name: Adıyaman, latitude: 37.7636, ...}, ...]
		```

	-	**Find all cities**

		```rb
		import io.github.burakpadr.turkeylocation4j.City;

		City.fromNoFilter(); # => [{name: Adana, latitude: 35.3308, ...}, ...]
		```

- **District API**

	- **Find districts by city name** 

	```rb
	import  io.github.burakpadr.turkeylocation4j.District;

	District.fromCityName("istanbul"); # => [{name: Adalar}, {name: Arnavutköy}, ...]
	```

-	**Neighborhood API**

	- **Find neighborhoods by city name and district name**

	```rb
	import  io.github.burakpadr.turkeylocation4j.Neighborhood;

	Neighborhood.fromCityNameAndDistrictName("istanbul", "beşiktaş"); # => [{name: Abbasağa Mah, postCode: 34022}, ...]
	```
