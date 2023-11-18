package de.mlosoft.filipclub.util;

import java.util.ArrayList;
import java.util.List;

import com.github.dozermapper.core.Mapper;

public class DozerHelper {

    /**
     * default constructor
     */
    private DozerHelper() {
    }

    /**
     * mapping static method
     * 
     * @param mapper
     *                 Dozer mapper bean
     * @param source
     *                 source list of objects
     * @param destType
     *                 destination class type
     * @param <T>
     *                 Source class
     * @param <U>
     *                 destination class type
     * @return a list of objects
     */
    public static <T, U> List<U> map(final Mapper mapper, final Iterable<T> source, final Class<U> destType) {

        final List<U> dest = new ArrayList<U>();

        for (T element : source) {
            if (element == null) {
                continue;
            }
            dest.add(mapper.map(element, destType));
        }

        // finally remove all null values if any
        List<U> s1 = new ArrayList<U>();
        s1.add(null);
        dest.removeAll(s1);

        return dest;
    }
}