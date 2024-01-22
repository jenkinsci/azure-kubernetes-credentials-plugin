package io.jenkins.plugins.azurecredsk8s;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import com.cloudbees.plugins.credentials.CredentialsScope;
import com.microsoft.azure.util.AzureImdsCredentials;
import io.fabric8.kubernetes.api.model.Secret;
import io.fabric8.kubernetes.client.utils.Serialization;
import io.jenkins.plugins.azurecredentialsk8s.AzureEnvironments;
import io.jenkins.plugins.azurecredentialsk8s.AzureManagedIdentityCredentialsConvertor;
import java.io.InputStream;
import java.util.ArrayList;
import org.junit.Test;

public class AzureManagedIdentityCredentialsConvertorTest {

    @Test
    public void canConvert() throws Exception {
        AzureManagedIdentityCredentialsConvertor convertor = new AzureManagedIdentityCredentialsConvertor();

        assertThat("correct registration of valid type", convertor.canConvert("azureManagedIdentity"), is(true));
        assertThat("incorrect type is rejected", convertor.canConvert("something"), is(false));
    }

    @Test
    public void canConvertAValidSecret() throws Exception {
        AzureManagedIdentityCredentialsConvertor convertor = new AzureManagedIdentityCredentialsConvertor();

        try (InputStream is = get("validManagedIdentity.yaml")) {
            Object secrets = Serialization.unmarshal(is, Secret.class);

            for (Secret secret : (ArrayList<Secret>) secrets) {
                assertThat("The Secret was loaded correctly from disk", notNullValue());
                AzureImdsCredentials credential = convertor.convert(secret);
                assertThat(credential, notNullValue());

                assertThat("credentials is using SYSTEM scope", credential.getScope(), is(CredentialsScope.SYSTEM));

                assertThat("credential id is mapped correctly", credential.getId(), is("azure-managed-identity"));
                assertThat(
                        "credential description is mapped correctly",
                        credential.getDescription(),
                        is("Azure managed identity"));
                assertThat("credential text is mapped correctly", credential.getSubscriptionId(), is("1234456457"));
                assertThat("credential text is mapped correctly", credential.getClientId(), is("client-id"));

                assertThat(
                        "Azure environment are defined",
                        credential.getAzureEnvironmentName(),
                        is(AzureEnvironments.AZURE.label));
            }
        }
    }

    @Test
    public void nonDefaultAzureEnv() throws Exception {
        AzureManagedIdentityCredentialsConvertor convertor = new AzureManagedIdentityCredentialsConvertor();

        try (InputStream is = get("nonDefaultAzureEnvironment.yaml")) {
            Secret secret = Serialization.unmarshal(is, Secret.class);
            assertThat("The Secret was loaded correctly from disk", notNullValue());
            AzureImdsCredentials credential = convertor.convert(secret);
            assertThat(credential, notNullValue());

            assertThat(
                    "Azure environment are defined",
                    credential.getAzureEnvironmentName(),
                    is(AzureEnvironments.AZURE_CHINA.label));
        }
    }

    private static final InputStream get(String resource) {
        InputStream is = AzureManagedIdentityCredentialsConvertorTest.class.getResourceAsStream(
                "AzureManagedIdentityCredentialsConvertorTest/" + resource);
        if (is == null) {
            fail("failed to load resource " + resource);
        }
        return is;
    }
}
