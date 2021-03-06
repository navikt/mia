const { getNetworkIp } = require('./getNetworkIp');
const driver = require('chromedriver');

module.exports = {
    default: {
        isLocal: true,
    },

    beforeEach: function(browser, done){
        if (!this.isLocal){
            getNetworkIp()
                .then(ip => browser.globals.launch_url = `http://${ip}:8800/mia`)
                .then(() => done())
                .catch(error => done(error))
        }
        else done();
    },

    before: function(done) {
        if(this.isLocal) driver.start();
        setTimeout(function() {
            done();
        }, 2000);

    },

    after: function(done) {
        if(this.isLocal) driver.stop();
        setTimeout(function() {
            done();
        }, 200);
    },
};
