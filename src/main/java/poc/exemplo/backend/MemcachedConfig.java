package poc.exemplo.backend;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.springframework.beans.factory.annotation.Value; // <-- IMPORT CORRETO
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.spy.memcached.MemcachedClient; // <-- IMPORT DA BIBLIOTECA SPYMEMCACHED

@Configuration
@EnableCaching
public class MemcachedConfig {

    @Value("${memcached.host}")
    private String memcachedHost;

    @Value("${memcached.port}")
    private int memcachedPort;

    @Bean
    public MemcachedClient memcachedClient() throws IOException {
        return new MemcachedClient(
                new InetSocketAddress(memcachedHost, memcachedPort));
    }
    
}
