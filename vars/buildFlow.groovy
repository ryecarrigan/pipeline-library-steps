void call(Closure body) {
    String label = randomPodLabel()
    stage('Linting Step') {
        def yaml = ''
        def cloud_name = 'kubernetes'
        podTemplate(
            label: label,
            yaml: yaml,
            cloud: cloud_name,
        ) {
            echo("Bonjour")
            body()
        }
    }
}

String randomPodLabel() {
    int max = /* Kubernetes limit */ 63 - /* hyphen and suffix */ 6;
    String suffix = UUID.randomUUID().toString()[0..4]

    String lcJobName = JOB_NAME.toLowerCase()

    if (lcJobName.length() > max) {
        podLabel = lcJobName[0..lcJobName.indexOf('/')] +
            lcJobName.reverse()[0..lcJobName.reverse().indexOf('/')].reverse()
    } else {
        podLabel = lcJobName
    }

    sanitizedPodLabel = podLabel.replaceAll('[^_.a-zA-Z0-9-]', '_').replaceFirst('^[^a-zA-Z0-9]', 'x')

    String label = sanitizedPodLabel + '-' + suffix

    label
}
