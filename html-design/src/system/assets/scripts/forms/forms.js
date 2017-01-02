
$(document).ready(function() {
  $(".alpaca-form").each(function() {
      var formId = $(this).attr("id");
      var formDefinition = window["alpaca_form_" + formId];
      $(this).alpaca(formDefinition);

      // Insert CSRF token (if any) into the form
      //
      var csrf = $("#csrf-" + formId + " input");
      if ( csrf != undefined ) {
        $(this).find("form").each(function () {
            $(this).append(csrf);
        });
      }
  });
});
