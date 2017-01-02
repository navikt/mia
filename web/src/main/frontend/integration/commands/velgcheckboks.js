exports.command = function(page, id, client, callback) {
    var self = this;

    page.api
        .click(`#${id} + label`)
        .pause(500)
        .checkboxervalgt(page, id, client);

    this.execute(function(result){
            if(typeof callback === "function") {
                callback.call(self, result);
            }
        });

    return this;
};