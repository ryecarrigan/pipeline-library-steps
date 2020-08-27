void call(Closure body) {
    String label = randomPodLabel()
    stage('Linting Step') {
        def yaml = """
apiVersion: "v1"
kind: "Pod"
spec:
  containers:
    image: "cloudbees/cloudbees-core-agent:2.235.5.1"
    name: "jnlp"
    resources:
      requests:
        cpu: "100m"
        memory: "256Mi"
    volumeMounts:
    - mountPath: "/home/jenkins/agent"
      name: "workspace-volume"
      readOnly: false
  nodeSelector:
    kubernetes.io/os: "linux"
  restartPolicy: "Never"
  volumes:
  - emptyDir:
      medium: ""
    name: "workspace-volume"
"""
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
