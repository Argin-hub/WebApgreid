package my.library.jdbc;

import my.library.jdbc.exception.ResourcesException;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class ResourcesQueue<Connection> {

    private final Semaphore semaphore; // семафор для разграничения доступа
    // список инициализированных коннектов:
    private final Queue<Connection> resource = new ConcurrentLinkedQueue<>();

    private final int timeOut; // тайм-аут для семафора

    public ResourcesQueue(int count, int timeOut) {
        semaphore = new Semaphore(count, true);
        this.timeOut = timeOut;
    }


    public Connection takeResource() throws ResourcesException {
        try {
            if (semaphore.tryAcquire(timeOut, TimeUnit.SECONDS)) {
                return resource.poll();
            }
        } catch (InterruptedException e) {
            throw new ResourcesException("Semaphore timeout", e);
        }
        throw new ResourcesException("Semaphore timeout");
    }


    public void returnResource(Connection res) {
        resource.add(res);
        semaphore.release();
    }


    public void addResource(Connection connection) {
        resource.add(connection);
    }

    public int size() {
        return resource.size();
    }

    public Queue<Connection> getResources() {
        return resource;
    }
}


