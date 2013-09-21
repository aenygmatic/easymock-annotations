easymock-annotations
====================

Support library for EasyMock framework. It offers a Mockito style annotation usage for EasyMock.

Following annotations are supported.

- @Mock (org.easymock.Mock is also supported)
  - Fields annotated with any of @Mock annotations the framework will create a mock object and inject into the field.
  - org.easymock.MockType is supported for both annotation
  - mock names are supported
  
- @MockControl
  - Field annotated with @MockControl all the mock will be created by the IMockControl which will be injected into the field.
  - org.easymock.MockType is supported for this annotation

  
- @Injected (org.easymock.TestSubject is also supported)
  - Mocked objects will be injected into the field annotated with @Injected. If the object has default constructor the field will be automaticaly instantiated. If the field is already instantiated only injection will be performed.

Injection strategy:
  - Field of @Injected object will be injected with mock which is the closest in the inheritance tree.
  - When two or more object has the same distance mock will be injected by name
  - Final ans static field are skipped
  
  
Exsample:

```java
public class TestedObjectTest {

    @Mock
    private Component component;
    @Mock
    private OtherComponent otherComponent;
    @Injected
    private TestedObject underTest;

    @Before
    public void setUp() {
        EasyMockAnnotations.initialize(this);
    }
    
    ...
}
```

Multiple controls can be used for grouping mocks:

```java
public class TestedObjectTest {

    @MockControl
    private IMocksControl control
    @MockControl
    private IMocksControl otherControl

    @Mock(control = "control")
    private Component component;
    @Mock(control = "otherControl")
    private OtherComponent otherComponent;
    
    @Injected
    private TestedObject underTest;

    @Before
    public void setUp() {
        EasyMockAnnotations.initialize(this);
    }
    
    ...
}
```
