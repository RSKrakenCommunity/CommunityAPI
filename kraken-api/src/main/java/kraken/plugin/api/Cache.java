package kraken.plugin.api;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Provides access to the cache. All access to cache game memory must be done
 * on the engine thread to prevent memory corruption.
 */
public final class Cache {

    /**
     * A cache of items.
     */
    private static final Map<Integer, CacheItem> items = new HashMap<>();
    /**
     * A cache of objects.
     */
    private static final Map<Integer, CacheObject> objects = new HashMap<>();
    /**
     * A cache of NPCs.
     */
    private static final Map<Integer, CacheNpc> npcs = new HashMap<>();
    /**
     * A lock for accessing the cache.
     */
    private static final ReentrantLock lock = new ReentrantLock();

    private Cache() {
    }

    /**
     * Calls some code within the cache lock.
     *
     * @param cb The callback to run within the lock.
     */
    private static <T> T locked(LockCallback<T> cb) {
        lock.lock();
        T t = cb.call();
        lock.unlock();
        return t;
    }

    /**
     * Loads an item asynchronously. The item will be loaded later on
     * in the engine thread.
     *
     * @param item The item to load.
     */
    public static native void asyncLoad(CacheItem item);


    /**
     * Loads an object asynchronously. The object will be loaded later on
     * in the engine thread.
     *
     * @param object The object to load.
     */
    public static native void asyncLoad(CacheObject object);


    /**
     * Loads an NPC asynchronously. The NPC will be loaded later on
     * in the engine thread.
     *
     * @param npc The NPC to load.
     */
    public static native void asyncLoad(CacheNpc npc);

    /**
     * Loads an item synchronously.
     *
     * @param item The item to load.
     */
    public static void load(CacheItem item) {
        asyncLoad(item);
        while (!item.isLoaded()) {
            Time.waitFor(10);
        }
    }

    /**
     * Loads an object synchronously.
     *
     * @param object The object to load.
     */
    public static void load(CacheObject object) {
        asyncLoad(object);
        while (!object.isLoaded()) {
            Time.waitFor(10);
        }
    }

    /**
     * Loads an NPC synchronously.
     *
     * @param npc The NPC to load.
     */
    public static void load(CacheNpc npc) {
        asyncLoad(npc);
        while (!npc.isLoaded()) {
            Time.waitFor(10);
        }
    }

    /**
     * Resets all in-memory caches.
     */
    public static void reset() {
        locked(() -> {
            items.clear();
            return null;
        });
    }

    /**
     * Retrieves an item from the cache. The fields within this item
     * may not immediately be available if this item has not been
     * retrieved before.
     *
     * @param id The id of the item to retrieve.
     * @param async If the request will be performed asynchronously.
     * @return The retrieved item.
     */
    public static CacheItem getItem(int id, boolean async) {
        if (id < 0) {
            return null;
        }

        return locked(() -> {
            CacheItem ret;
            if (!items.containsKey(id)) {
                ret = new CacheItem(id);
                if (async) {
                    asyncLoad(ret);
                } else {
                    load(ret);
                }

                items.put(id, ret);
            } else {
                ret = items.get(id);
            }
            return ret;
        });
    }

    /**
     * Retrieves an item from the cache.
     *
     * @param id The id of the item to retrieve.
     * @return The retrieved item.
     */
    public static CacheItem getItem(int id) {
        return getItem(id, true);
    }

    /**
     * Retrieves an object from the cache. The fields within this object
     * may not immediately be available if this object has not been
     * retrieved before.
     *
     * @param id The id of the object to retrieve.
     * @param async If the request will be performed asynchronously.
     * @return The retrieved object.
     */
    public static CacheObject getObject(int id, boolean async) {
        if (id < 0) {
            return null;
        }

        return locked(() -> {
            CacheObject ret;
            if (!objects.containsKey(id)) {
                ret = new CacheObject(id);
                if (async) {
                    asyncLoad(ret);
                } else {
                    load(ret);
                }

                objects.put(id, ret);
            } else {
                ret = objects.get(id);
            }
            return ret;
        });
    }

    /**
     * Retrieves an object from the cache.
     *
     * @param id The id of the object to retrieve.
     * @return The retrieved object.
     */
    public static CacheObject getObject(int id) {
        return getObject(id, true);
    }

    /**
     * Retrieves an NPC from the cache. The fields within this NPC
     * may not immediately be available if this NPC has not been
     * retrieved before.
     *
     * @param id The id of the NPC to retrieve.
     * @param async If the request will be performed asynchronously.
     * @return The retrieved NPC.
     */
    public static CacheNpc getNpc(int id, boolean async) {
        if (id < 0) {
            return null;
        }

        return locked(() -> {
            CacheNpc ret;
            if (!npcs.containsKey(id)) {
                ret = new CacheNpc(id);
                if (async) {
                    asyncLoad(ret);
                } else {
                    load(ret);
                }

                npcs.put(id, ret);
            } else {
                ret = npcs.get(id);
            }
            return ret;
        });
    }

    /**
     * Retrieves an NPC from the cache.
     *
     * @param id The id of the NPC to retrieve.
     * @return The retrieved NPC.
     */
    public static CacheNpc getNpc(int id) {
        return getNpc(id, true);
    }

    /**
     * Determines if anything is currently loading.
     *
     * @return If anything is currently loading.
     */
    public static boolean loading() {
        return locked(() -> {
            for (CacheItem item : items.values()) {
                if (!item.isLoaded()) {
                    return true;
                }
            }

            for (CacheNpc npc : npcs.values()) {
                if (!npc.isLoaded()) {
                    return true;
                }
            }

            for (CacheObject object : objects.values()) {
                if (!object.isLoaded()) {
                    return true;
                }
            }

            return false;
        });
    }

    private interface LockCallback<T> {
        T call();
    }
}
