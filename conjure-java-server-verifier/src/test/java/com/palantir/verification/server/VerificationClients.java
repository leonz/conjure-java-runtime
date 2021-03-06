/*
 * (c) Copyright 2018 Palantir Technologies Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.palantir.verification.server;

import com.palantir.conjure.java.client.jaxrs.JaxRsClient;
import com.palantir.conjure.java.dialogue.serde.DefaultConjureRuntime;
import com.palantir.conjure.java.okhttp.NoOpHostEventsSink;
import com.palantir.conjure.verification.client.VerificationClientService;
import com.palantir.dialogue.Channel;
import com.palantir.dialogue.hc4.ApacheHttpClientChannels;

public final class VerificationClients {
    private VerificationClients() {}

    public static VerificationClientService verificationClientService(VerificationClientRule verificationClientRule) {
        return JaxRsClient.create(
                VerificationClientService.class,
                verificationClientRule.getClientConfiguration().userAgent().orElseThrow(IllegalArgumentException::new),
                NoOpHostEventsSink.INSTANCE,
                verificationClientRule.getClientConfiguration());
    }

    public static VerificationClientService verificationClientServiceDialogue(
            VerificationClientRule verificationClientRule) {
        Channel channel = ApacheHttpClientChannels.create(verificationClientRule.getClientConfiguration());
        return JaxRsClient.create(
                VerificationClientService.class,
                channel,
                DefaultConjureRuntime.builder().build());
    }
}
