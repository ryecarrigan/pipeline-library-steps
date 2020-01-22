def call(String eventTrigger) {
  if (!eventTrigger) {
    return ''
  }

  return """<!-- JMESPath Event Trigger -->
<com.cloudbees.jenkins.plugins.pipeline.events.EventTrigger>
	<spec/>
	<condition class="com.cloudbees.jenkins.plugins.pipeline.events.conditions.QueryTriggerCondition">
		<query>${eventTrigger}</query>
	</condition>
</com.cloudbees.jenkins.plugins.pipeline.events.EventTrigger>
"""

}
