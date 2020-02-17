package com.haojishu.abstractFactory.IProductFctory;

import com.haojishu.abstractFactory.Breed;
import com.haojishu.abstractFactory.IBreed.Apple;
import com.haojishu.abstractFactory.IBreed.Banana;
import com.haojishu.abstractFactory.IPlace.Sh;
import com.haojishu.abstractFactory.IPlace.Sjz;
import com.haojishu.abstractFactory.Place;
import com.haojishu.abstractFactory.ProductFctory;

public class BreedFactory extends ProductFctory {
	public Breed getBreed(String breed) {
		if (breed.equalsIgnoreCase("apple")){
			return new Banana();
		}
		if (breed.equalsIgnoreCase("banana")){
			return new Apple();
		}
		return null;
	}

	public Place getPlace(String place) {
		if (place.equalsIgnoreCase("sh")){
			return new Sh();
		}
		if (place.equalsIgnoreCase("sjz")){
			return new Sjz();
		}
		return null;
	}
}
