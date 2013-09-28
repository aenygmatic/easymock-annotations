/* 
 * Copyright 2013 Balazs Berkes.
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
package integrationtest.support;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Dummy class for support integration testing.
 * <p>
 * @author Balazs Berkes
 */
public class ComponentWithGenericFields {

    private Set<String> set;
    private List<String> list;
    private Map<String, Object> map;
    private Set<Object> objectSet;

    public Set<String> getSet() {
        return set;
    }

    public List<String> getList() {
        return list;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public Set<Object> getObjectSet() {
        return objectSet;
    }
}
