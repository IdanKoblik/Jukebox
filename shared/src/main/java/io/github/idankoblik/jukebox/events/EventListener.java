package io.github.idankoblik.jukebox.events;

import org.jetbrains.annotations.ApiStatus;

import java.lang.reflect.ParameterizedType;

/**
 * An abstract class representing an event listener for a specific type of {@link Event}.
 * @param <E> the type of event that this listener can handle, which must extend {@link Event}
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
     * An abstract method for handling specified event
     * @param event type of the event
     */
    public abstract void handle(E event);

    /**
     * Returns event type
     * @return event type
     */
    public Class<E> getType() {
        return type;
    }
}
