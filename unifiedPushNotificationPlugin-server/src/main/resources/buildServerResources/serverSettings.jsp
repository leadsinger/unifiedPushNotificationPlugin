<%@ include file="/include.jsp"%>

<script type="text/javascript">
  function saveSettings(form)
  {
     var server_url = form.server_url.value;
     var application_id = form.application_id.value;
     var master_secret = form.master_secret.value;

     BS.ajaxRequest($('saveSettingsForm').action, {
        parameters: 'server_url=' + server_url + '&application_id=' + application_id + '&master_secret=' + master_secret,
        onComplete: function(transport) {
        if (transport.responseXML) {
                  $('saveSettingsContainer').refresh();
              }
            }
        });
  }
</script>

<bs:refreshable containerId="saveSettingsContainer" pageUrl="${pageUrl}">
<c:url var="actionUrl" value="/saveSettings.html"/>
<form action="${actionUrl}" id="saveSettingsForm" method="POST" >
    Server-URL: <input type="text" name="server_url" value="${server_url}" />
    <br />
    Application-ID: <input type="text" name="application_id" value="${application_id}" />
    <br />
    Master-Secret: <input type="text" name="master_secret" value="${master_secret}" />
    <br />
    <input type="button" value="Save" class="btn btn_primary submitButton" onClick="saveSettings(this.form)"/>
</form>
<bs:messages key="settingsMessage"/>
</bs:refreshable>