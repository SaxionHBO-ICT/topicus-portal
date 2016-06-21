package nl.saxion.jelmer.topitalk.model;

import android.support.annotation.NonNull;

/**
 * Interface to compel certain classes to implement a method
 * to enable timestamping of objects.
 */
public interface Datable {
    @NonNull
    String generateDate();
}
