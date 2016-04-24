'use strict';
module.exports = function(grunt){
  require('load-grunt-tasks')(grunt);
  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.loadNpmTasks('grunt-contrib-sass');

  grunt.initConfig({
    sass: {                              // Task 
      dist: {                            // Target 
        options: {                       // Target options 
          style: 'expanded'
        },
        files: {                         // Dictionary of files 
          'public/static/dist/main.css': 'public/static/styles/main.scss',       // 'destination': 'source' 
        }
      }
    },
    watch: {
      scripts: {
        files: ['**/*.scss'],
        tasks: ['sass'],
        options: {
          spawn: false,
        },
      },
    }
  });

  grunt.registerTask('default', ['sass']);
};
