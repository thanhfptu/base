/* TODO: Mở lại nếu dùng Elastic Search
package vn.edu.fpt.capstone.api.configs.es;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.data.elasticsearch.core.convert.MappingElasticsearchConverter;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchMappingContext;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.util.stream.Stream;

@Configuration
@RequiredArgsConstructor
@EnableElasticsearchRepositories(basePackages = "vn.edu.fpt.capstone.api.repositories.es")
public class ESConfig {

    @Value("${elasticsearch.hosts}")
    private final String[] hosts;

    @Bean
    public RestClient restClient() {
        HttpHost[] httpHosts = Stream.of(this.hosts)
                .map(HttpHost::create)
                .toArray(HttpHost[]::new);
        return RestClient.builder(httpHosts).build();
    }

    @Bean
    public ElasticsearchTransport elasticsearchTransport(RestClient restClient) {
        return new RestClientTransport(restClient, new JacksonJsonpMapper());
    }

    @Bean
    public ElasticsearchClient elasticsearchClient(ElasticsearchTransport transport) {
        return new ElasticsearchClient(transport);
    }

    @Bean
    public ElasticsearchConverter elasticsearchConverter() {
        return new MappingElasticsearchConverter(new SimpleElasticsearchMappingContext());
    }

    @Bean
    public ElasticsearchTemplate elasticsearchTemplate(ElasticsearchClient client, ElasticsearchConverter converter) {
        return new ElasticsearchTemplate(client, converter);
    }

}
*/
