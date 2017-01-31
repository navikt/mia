exports.command = function(page, callback) {
    var self = this;
    page.expect.element(".skjema-feilmelding").to.not.be.present;
    this.execute(function(result){
        if(typeof callback === "function") {
            callback.call(self, result);
        }
    });

    return this;
};