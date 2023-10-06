/*
 * Copyright (C) 2023 /e/ foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.vending.licensing;

// Android library projects do not yet support AIDL, so this has been
// precompiled into the src directory.
oneway interface ILicenseV2ResultListener {
  void verifyLicense(int responseCode, in Bundle responsePayload);
}