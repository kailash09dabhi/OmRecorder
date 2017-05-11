/**
 * Copyright 2017 Kailash Dabhi (Kingbull Technology)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package omrecorder;

/**
 * A {@code ThreadAction} is an action which going to be executed on the implementer thread.
 *
 * @author Kailash Dabhi
 * @date 25-07-2016
 */
interface ThreadAction {
  /**
   * Execute {@code runnable} action on implementer {@code Thread}
   */
  void execute(Runnable action);
}

