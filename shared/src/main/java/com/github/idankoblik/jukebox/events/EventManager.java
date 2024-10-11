package com.github.idankoblik.jukebox.events;

import org.jetbrains.annotations.ApiStatus;

import java.util.*;

/**
 * Manages the registration and firing of internal.events to registered listeners.
 * <p>
 * The {@code EventManager} follows the singleton design pattern, ensuring
 * that there is only one instance throughout the application.
 * </p>
 *
 */
@ApiStatus.AvailableSince("0.0.3")
public class EventManager {

    private static EventManager instance;

    /**
     * Retrieves the singleton instance of the {@code EventManager}.
     *
     * @return the singleton instance of {@code EventManager}.
     */
    public static EventManager getInstance() {
        if (instance == null)
            instance = new EventManager();

        return instance;
    }

    private final Map<Class<? extends Event>, List<EventListener<? extends Event>>> listeners = new HashMap<>();

    /**
     * Registers a listener to receive internal.events.
     *
     * @param listener the {@link EventListener} to register; cannot be null.
     * @param <E>      the type of the event this listener handles.
     */
    public <E extends Event> void registerListener(EventListener<E> listener) {
        this.listeners.computeIfAbsent(listener.getType(), arr -> new ArrayList<>()).add(listener);
    }

    /**
     * Fires an event to all registered listeners.
     *
     * @param event the event to be fired; cannot be null.
     */
    @SuppressWarnings("unchecked")
    public void fireEvent(Event event) {
        List<EventListener<? extends Event>> eventListeners = listeners.get(event.getClass());
        if (eventListeners == null)
            return;

        eventListeners.forEach(listener -> {
            EventListener<Event> typedListener = (EventListener<Event>) listener;
            typedListener.handle(event);
        });
    }

    /**
     * Retrieves all registered listeners merged into a single list.
     *
     * @return a list containing all registered listeners for all event types.
     */
    public List<EventListener<? extends Event>> getRegisteredListeners() {
        List<EventListener<? extends Event>> allListeners = new ArrayList<>();
        for (List<EventListener<? extends Event>> listenerList : listeners.values())
            allListeners.addAll(listenerList);

        return Collections.unmodifiableList(allListeners);
    }

    /**
     * @hidden
     */
    public void clear() {
        this.listeners.clear();
    }
}
