package com.expedia.fault.injector.dropwizard;

import com.expedia.fault.injector.FaultInjector;

import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Mockito;

import io.dropwizard.testing.junit.ResourceTestRule;
import javax.ws.rs.client.Entity;

import static org.assertj.core.api.Assertions.assertThat;

public class FaultInjectorResourceTest {
    private static final FaultInjector faultInjector = Mockito.mock(FaultInjector.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new FaultInjectorResource(faultInjector))
            .build();

    @Test
    public void testEndPointExists() {
        final Entity emptyJson = Entity.json("{}");
        assertThat(resources.target("/__fault").request().get().getStatus()).isEqualTo(200);
        assertThat(resources.target("/__fault/cpu").request().post(emptyJson).getStatus()).isEqualTo(200);
        assertThat(resources.target("/__fault/memory").request().post(emptyJson).getStatus()).isEqualTo(200);
        assertThat(resources.target("/__fault/memory/reset").request().post(emptyJson).getStatus()).isEqualTo(200);
        assertThat(resources.target("/__fault/disk").request().post(emptyJson).getStatus()).isEqualTo(200);
    }
}
