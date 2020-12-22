def call(Map args = [:]) {
  sh 'mvn compile test-compile'
}
