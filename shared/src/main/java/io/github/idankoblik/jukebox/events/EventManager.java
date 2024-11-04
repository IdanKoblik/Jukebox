package io.github.idankoblik.jukebox.events;

import org.jetbrains.annotations.ApiStatus;

import java.util.*;

/**
 * A class for managing all events
 */
@ApiStatus.AvailableSince("0.0.3")
public class EventManager {

    private final Map<Class<? extends Event>, List<EventListener<? extends Event>>> listeners = new HashMap<>();

    /**
     * Register a specified event
     * @param listener the listener to be registered
     * @param <E> type of the event {@link Event}
     */
    public <E extends Event> void registerListener(EventListener<E> listener) {
        this.listeners.computeIfAbsent(listener.getType(), arr -> new ArrayList<>()).add(listener);
    }

    /**
     * Fires an even
     * @param event the event to be fired
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
     * Returns all listener that has been registered
     * @return all registered events
     */
    public List<EventListener<? extends Event>> getRegisteredListeners() {
        List<EventListener<? extends Event>> allListeners = new ArrayList<>();
        for (List<EventListener<? extends Event>> listenerList : listeners.values())
            allListeners.addAll(listenerList);

        return Collections.unmodifiableList(allListeners);
    }

    /**
     * Remove all registered listeners
     */
    public void clear() {
        this.listeners.clear();
    }
}
