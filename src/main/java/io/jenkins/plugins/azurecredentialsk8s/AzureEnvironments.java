/*
 * The MIT License
 *
 * Copyright 2024 Tue Dissing
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.jenkins.plugins.azurecredentialsk8s;

public enum AzureEnvironments {
    AZURE("Azure"),
    AZURE_CHINA("Azure China"),
    AZURE_US_GOVERNMENT("Azure US Government");

    public final String label;

    private AzureEnvironments(String label) {
        this.label = label;
    }

    /**
     * Finds the environment based on its label. Defaults to "Azure"
     * @param label The label of an AzureEnvironment
     * @return AzureEnvironment
     */
    public static AzureEnvironments valueOfLabel(String label) {
        for (AzureEnvironments azureEnvironments : values()) {
            if (azureEnvironments.label.equals(label)) {
                return azureEnvironments;
            }
        }
        // defaults to "Azure"
        return valueOf(AzureEnvironments.AZURE.label);
    }
}
