package br.com.conexasaude.agenda.util;

import java.util.HashMap;

public class MapBuilder extends HashMap{

    private MapBuilder() {
    }

    public static MapBuilder build() {
        return new MapBuilder();
    }

    public static MapBuilder build(Object k, Object o) {
        MapBuilder build = new MapBuilder();
        build.put(k, o);
        return build;
    }

    public MapBuilder add(Object k, Object o) {
        this.put(k, o);
        return this;
    }


}
