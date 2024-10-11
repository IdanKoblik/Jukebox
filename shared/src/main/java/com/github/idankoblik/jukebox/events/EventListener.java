package com.github.idankoblik.jukebox.events;

import org.jetbrains.annotations.ApiStatus;

import java.lang.reflect.ParameterizedType;

/**
 * A class for handling internal.events of a specific type.
 *
 * @param <E> the type of event this listener can handle, which must extend the {@link Event} class.
 *
 */
@ApiStatus.AvailableSince("0.0.3")
public abstract class EventListener<E extends Event> {

    private final Class<E> type;

    @SuppressWarnings("unchecked")
    public EventListener() {
        try {
            this.type = (Class<E>) Class.forName(((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0].getTypeName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Handles the specified event.
     *
     * @param event the event to be handled; cannot be null.zz
     */
    public abstract void handle(E event);

    public Class<E> getType() {
        return type;
    }
}
