/***
 * Copyright 2013 Teoti Graphix, LLC.
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
 * 
 * 
 * @author Michael Schmalle <mschmalle@teotigraphix.com>
 */

package randori.compiler.driver;

import org.apache.flex.compiler.projects.IASProject;
import org.apache.flex.compiler.targets.ITargetProgressMonitor;
import org.apache.flex.compiler.targets.ITargetSettings;

/**
 * The {@link IRandoriBackend} API allows clients of the compiler such as the
 * {@link IRandoriApplication} access to factory methods to create emitters,
 * writers and visitors for source code generation.
 * 
 * @author Michael Schmalle
 */
public interface IRandoriBackend extends IBackend
{
    @Override
    IRandoriTarget createTarget(IASProject project, ITargetSettings settings,
            ITargetProgressMonitor monitor);
}
