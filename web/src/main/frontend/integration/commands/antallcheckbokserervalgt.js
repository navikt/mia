exports.command = function (page, antall, callback) {
    var self = this;
    page.api.elements("css selector", "input:checked", (result)=> {
        page.api.assert.equal(result.value.length, antall);
    });

    this.execute(function (result) {
            if (typeof callback === "function") {
                callback.call(self, result);
            }
        });

    return this;
};