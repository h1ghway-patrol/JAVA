package coursehub.practice6.strings;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class StringAndCodeTest {
    @Test void textEqualityDoesNotRequireSameReference() {
        assertTrue(StringIdentityDemo.sameText(new String("JAVA"), "JAVA"));
        assertFalse(StringIdentityDemo.sameReference(new String("JAVA"), "JAVA"));
    }
    @Test void literalsCompileTimeConcatenationAndInternHaveExpectedText() {
        String first = "Java";
        String second = "Ja" + "va";
        String runtimePart = "Ja";
        String runtime = runtimePart + "va";
        assertSame(first, second);
        assertEquals(first, runtime);
        assertNotSame(first, runtime);
        assertSame(first, runtime.intern());
    }
    @Test void plusAndBuilderProduceSameReport() {
        List<String> lines = java.util.stream.IntStream.range(0, 10_000).mapToObj(i -> "line-" + i).toList();
        assertEquals(ReportBuilder.buildWithPlus(lines), ReportBuilder.buildWithBuilder(lines));
        assertEquals(10_000, ReportBuilder.compareOnce(lines).resultLength() > 0 ? lines.size() : 0);
    }
    @Test void courseCodeRequiresWholeStringMatch() {
        assertEquals(new CourseCode("JAVA", 101), CourseCode.parse("JAVA-101"));
        for (String bad : List.of("JAVA-10", "xJAVA-101", "JAVA-101x", "JAVA-１０１", " JAVA-101", "JAVA-101 "))
            assertThrows(IllegalArgumentException.class, () -> CourseCode.parse(bad));
    }
}
