package io.github.drmanan.whisper;

import java.util.ArrayList;
import java.util.List;

public class DbObservable {

    List<DbObserver> observers;

    public void register(DbObserver observer) {
        if (observers == null)
            observers = new ArrayList<>();

        if (!observers.contains(observer))
            observers.add(observer);
    }

    public void unregister(DbObserver observer) {
        if (observers == null) return;
        observers.remove(observer);
    }

    public void notifyObservers() {
        try {
            if (observers == null) return;

            for (DbObserver observer : observers) {
                if (observer != null) observer.onChange();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
