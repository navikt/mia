const driver = require('./driver');

module.exports = {

    before: function(done) {
        driver.start();
        setTimeout(function() {
            done();
        }, 2000);

    },

    after: function(done) {
        driver.stop();
        setTimeout(function() {
            done();
        }, 200);
    },
};
