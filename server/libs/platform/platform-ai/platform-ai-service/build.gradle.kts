dependencies {
    implementation("org.apache.commons:commons-lang3")
    implementation("org.springframework.ai:spring-ai-core:${rootProject.libs.versions.spring.ai.get()}")
    implementation(project(":server:libs:core:commons:commons-util"))
    implementation(project(":server:libs:platform:platform-ai:platform-ai-api"))
}
