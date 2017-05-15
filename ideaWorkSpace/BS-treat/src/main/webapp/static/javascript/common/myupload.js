/**
 * Created by joeshao on 16/9/20.
 */

;(function (global) {

    function RadioUpload(options) {
        var that = this;
        var defaults = {
            maxFileSize: 0,
            minWidth: 0,
            maxWidth: 0,
            minHeight: 0,
            maxHeight: 0,
            showRemove: true,
            showLoader: true,
            showErrors: true,
            errorsPosition: 'overlay',
            messages: {
                'default': '点击或拖动文件到此处',
                'replace': '点击或拖入文件来替换',
                'remove': '删除',
                'error': '系统出错了'
            },
            tpl: {
                wrap: '<div class="dropify-wrapper"></div>',
                loader: '<div class="dropify-loader"></div>',
                message: '<div class="dropify-message"><span class="file-icon" /> <p>{{ default }}</p></div>',
                preview: '<div class="dropify-preview"><span class="dropify-render"></span><div class="dropify-infos"><div class="dropify-infos-inner"><p class="dropify-infos-message">{{ replace }}</p></div></div></div>',
                filename: '<p class="dropify-filename"><span class="file-icon"></span> <span class="dropify-filename-inner"></span></p>',
                clearButton: '<button type="button" class="dropify-clear">{{ remove }}</button>',
                errorLine: '<p class="dropify-error">{{ error }}</p>',
                errorsContainer: '<div class="dropify-errors-container"><ul></ul></div>'
            }
        };
        this.input = $('#' + options.browse_button);
        this.valueInput = $('#' + options.file_input);
        this.element = this.input[0];
        this.wrapper = null;
        this.preview = null;
        this.filenameWrapper = null;
        this.settings = defaults;
        this.isInit = false;
        this.isDisabled = false;

        this.uploadedInfos = [];
        this.start = this.start.bind(this);
        this.clearElement = this.clearElement.bind(this);

        this.uploader = Qiniu.uploader({
            runtimes: 'html5',
            browse_button: options.browse_button,
            drop_element: options.browse_button,
            uptoken_url: base + '/file/uptoken',
            get_new_uptoken: true,
            unique_names: true,
            domain: domain,
            max_file_size: '10mb',
            max_retries: 3,
            chunk_size: '4mb',
            auto_start: false,
            multi_selection: false,
            filters: {
                mime_types: [{title: "图片文件", extensions: "jpg,jpeg,png"}]
            },
            init: {
                'FilesAdded': function (up, files) {
                    //单图上传时清空上传列表
                    if (up.files.length > 1) {
                        up.removeFile(up.files[0]);
                    }
                    //清空已上传的信息
                    if (that.uploadedInfos.length > 0) {
                        that.uploadedInfos = [];
                    }
                    plupload.each(files, function (file) {
                        that.resetPreview();
                        that.setPreview(file);

                    });
                },
                'FileUploaded': function (up, file, info) {
                    var res = $.parseJSON(info);
                    var key = res.key;
                    that.uploadedInfos.push({id: file.id, key: res.key});
                },
                'UploadComplete': function (up, files) {
                    $('.preloaderAjax').hide();
                    var keys = that.uploadedInfos.map(function (info) {
                        return info.key;
                    }).join(',');
                    if (keys.length > 0) {
                        $.ajax({
                            url: base + '/file/filesSave',
                            data: {keys: keys},
                            type: 'post',
                            error: function (XMLHttpRequest, textStatus, errorThrown) {
                                showNetErrorWarning();
                            },
                            success: function (data) {
                                if(data.flag) {
                                    var serverIds = data.data;
                                    for (var i = 0; i < serverIds.length; i++) {
                                        that.uploadedInfos[i].serverId = serverIds[i];
                                    }
                                    that.valueInput.val(serverIds.join(','));
                                    that.callback();
                                }
                                else {
                                    showErrorMessage('图片上传失败',data.msg);
                                }
                            }
                        });
                    }
                    else {
                        that.valueInput.val('');
                        that.callback();
                    }
                },
                'Error': function (up, err, errTip) {
                    $('.preloaderAjax').hide();
                    //上传出错时，处理相关的事情
                    showErrorMessage('图片上传失败',errTip);
                }
            }
        });

        this.translateMessages();
        this.createElements();
    }

    /**
     * 渲染说明文字
     */
    RadioUpload.prototype.translateMessages = function () {
        for (var name in this.settings.tpl) {
            for (var key in this.settings.messages) {
                this.settings.tpl[name] = this.settings.tpl[name].replace('{{ ' + key + ' }}', this.settings.messages[key]);
            }
        }
    };

    /**
     * 创建Dom
     */
    RadioUpload.prototype.createElements = function () {
        this.isInit = true;
        this.input.wrap($(this.settings.tpl.wrap));
        this.wrapper = this.input.parent();

        var messageWrapper = $(this.settings.tpl.message).insertBefore(this.input);
        $(this.settings.tpl.errorLine).appendTo(messageWrapper);

        if (this.isTouchDevice() === true) {
            this.wrapper.addClass('touch-fallback');
        }

        if (this.input.attr('disabled')) {
            this.isDisabled = true;
            this.wrapper.addClass('disabled');
        }

        if (this.settings.showLoader === true) {
            this.loader = $(this.settings.tpl.loader);
            this.loader.insertBefore(this.input);
        }

        this.preview = $(this.settings.tpl.preview);
        this.preview.insertAfter(this.input);

        if (this.isDisabled === false && this.settings.showRemove === true) {
            this.clearButton = $(this.settings.tpl.clearButton);
            this.clearButton.insertAfter(this.input);
            this.clearButton.on('click', this.clearElement);
        }

        this.filenameWrapper = $(this.settings.tpl.filename);
        this.filenameWrapper.prependTo(this.preview.find('.dropify-infos-inner'));

        if (this.settings.showErrors === true) {
            this.errorsContainer = $(this.settings.tpl.errorsContainer);

            if (this.settings.errorsPosition === 'outside') {
                this.errorsContainer.insertAfter(this.wrapper);
            } else {
                this.errorsContainer.insertBefore(this.input);
            }
        }

        if (this.input.data('serverid')) {
            this.setDefaultPreview(this.input.data('serverid'));
        }
    };

    /**
     * 删除文件存储信息
     */
    RadioUpload.prototype.removeFile = function () {
        if (this.uploader.files.length > 0) {
            this.uploader.removeFile(this.uploader.files[0]);
        }
        this.uploadedInfos = [];
    };

    /**
     * 移除图片展示并清除文件
     */
    RadioUpload.prototype.clearElement = function () {
        this.removeFile();
        this.resetPreview();
    };

    /**
     * 设置图片展示
     * @param file
     */
    RadioUpload.prototype.setPreview = function (file) {
        var fileNative = file.getNative();
        var URL = global.URL || global.webkitURL;
        var src = URL.createObjectURL(fileNative);

        this.wrapper.removeClass('has-error').addClass('has-preview');
        this.filenameWrapper.children('.dropify-filename-inner').html(file.name);
        var render = this.preview.children('.dropify-render');

        this.hideLoader();

        var imgTag = $('<img />').attr('src', src);

        if (this.settings.height) {
            imgTag.css("max-height", this.settings.height);
        }

        imgTag.appendTo(render);
        this.preview.fadeIn();
    };

    /**
     * 设置默认图片
     * @param src
     * @param serverId
     * @param key
     */
    RadioUpload.prototype.setDefaultPreview = function (serverId) {
        var that = this;
        $.ajax({
            url: base+'/file/getImageDetail',
            method: 'get',
            data: {serverId:serverId},
            dataType: 'json',
            success: function(data) {
                if(data.flag){
                    var imageDetail = data.data;
                    that.uploadedInfos = [{serverId: imageDetail.serverId, key: imageDetail.key}];

                    that.wrapper.removeClass('has-error').addClass('has-preview');
                    that.filenameWrapper.children('.dropify-filename-inner').html('');
                    var render = that.preview.children('.dropify-render');

                    that.hideLoader();

                    var imgTag = $('<img />').attr('src', imageDetail.src);

                    if (that.settings.height) {
                        imgTag.css("max-height", that.settings.height);
                    }

                    imgTag.appendTo(render);
                    that.preview.fadeIn();
                }
                else {
                    showErrorMessage('默认图片加载失败',data.msg);
                }
            },
            error: function() {
                showNetErrorWarning();
            }
        });

    };

    /**
     * 重置图片展示
     */
    RadioUpload.prototype.resetPreview = function () {
        this.wrapper.removeClass('has-preview');
        var render = this.preview.children('.dropify-render');
        render.find('.dropify-extension').remove();
        render.find('i').remove();
        render.find('img').remove();
        this.preview.hide();
        this.hideLoader();
    };

    /**
     * 禁止选择文件
     */
    RadioUpload.prototype.disable = function () {
        this.input.attr('disabled','disabled');
    };

    /**
     * 启用选择文件
     */
    RadioUpload.prototype.enable = function () {
        this.input.removeAttr('disabled');
    };

    /**
     * 文件上传列表是否为空
     * @returns {boolean}
     */
    RadioUpload.prototype.isEmpty = function () {
        return this.uploader.files.length == 0 && this.uploadedInfos.length == 0;
    };

    /**
     * 上传文件
     * @param callback
     */
    RadioUpload.prototype.start = function (callback) {
        $('.preloaderAjax').show();
        this.callback = callback;
        this.disable();
        this.uploader.start();
    };

    /**
     * Test if it's touch screen
     *
     * @return {Boolean}
     */
    RadioUpload.prototype.isTouchDevice = function()
    {
        return (('ontouchstart' in window)
        || (navigator.MaxTouchPoints > 0)
        || (navigator.msMaxTouchPoints > 0));
    };

    /**
     * Show the loader
     */
    RadioUpload.prototype.showLoader = function()
    {
        if (typeof this.loader !== "undefined") {
            this.loader.show();
        }
    };

    /**
     * Hide the loader
     */
    RadioUpload.prototype.hideLoader = function()
    {
        if (typeof this.loader !== "undefined") {
            this.loader.hide();
        }
    };


    function MultiUpload(options) {
        var that = this;
        this.uploadedInfos = [];

        this.return_type = 'json';
        if (options.return_type){
            this.return_type = options.return_type;
        }

        this.uploader = Qiniu.uploader({
            runtimes: 'html5',
            browse_button: options.browse_button,
            drop_element: options.browse_button,
            uptoken_url: base + '/file/uptoken',
            get_new_uptoken: true,
            unique_names: true,
            domain: domain,
            max_file_size: '10mb',
            max_retries: 3,
            chunk_size: '4mb',
            auto_start: false,
            multi_selection: true,
            filters: {
                mime_types: [{title: "图片文件", extensions: "jpg,jpeg,png"}]
            },
            init: {
                'FilesAdded': function (up, files) {

                    plupload.each(files, function (file) {
                        var fileNative = file.getNative();
                        var URL = global.URL || global.webkitURL;
                        var src = URL.createObjectURL(fileNative);
                        that.addFile(src,file.id,null);
                    });
                },
                'FileUploaded': function (up, file, info) {
                    var res = $.parseJSON(info);
                    var key = res.key;
                    that.uploadedInfos.push({id: file.id, key: res.key});
                },
                'UploadComplete': function (up, files) {

                    $('.preloaderAjax').hide();

                    var keys = that.uploadedInfos.map(function (info) {
                        return info.key;
                    }).join(',');
                    if (keys.length > 0) {
                        $.ajax({
                            url: base + '/file/filesSave',
                            data: {keys: keys},
                            type: 'post',
                            error: function (XMLHttpRequest, textStatus, errorThrown) {
                                showErrorMessage('网络异常，请稍后再试');
                            },
                            success: function (data) {
                                if (data.flag){
                                    var serverIds = data.data;
                                    for (var i = 0; i < serverIds.length; i++) {
                                        that.uploadedInfos[i].serverId = serverIds[i];
                                    }
                                    if (that.return_type == 'json'){
                                        that.valueInput.val(JSON.stringify(serverIds));
                                    }
                                    else {
                                        that.valueInput.val(serverIds.join(','));
                                    }
                                    that.callback();
                                }
                                else {
                                    showErrorMessage("上传失败",data.msg);
                                }
                            }
                        });
                    }
                    else {
                        that.valueInput.val('');
                        that.callback();
                    }
                },
                'Error': function (up, err, errTip) {
                    $('.preloaderAjax').hide();
                    //上传出错时，处理相关的事情
                    showErrorMessage(errTip);
                }
            }
        });

        this.dropzone = $('#'+options.browse_button);
        this.valueInput = $('#'+options.file_input);
        this.clearElements = this.clearElements.bind(this);
        this.createElements();
    }

    MultiUpload.prototype.createElements = function() {
        this.wrapper = $('<div class="multiupload-wrapper"></div>');
        this.message = $('<div class="multiupload-message"><span class="file-icon"></span><p>点击或拖动文件到此处</p></div>');
        this.wrapper.append(this.message);
        this.dropzone.append(this.wrapper);

        if (this.dropzone.data('serverids')){
            this.addDefaultImage(this.dropzone.data('serverids'));
        }
    };

    /**
     * 清除所有文件
     */
    MultiUpload.prototype.clearElements = function() {
        this.uploadedInfos = [];
        this.uploader.files.splice(0,this.uploader.files.length);
        this.wrapper.children('.multiupload-preview').remove();
        this.message.show();
    };

    /**
     * 添加默认文件
     * @param serverIds
     */
    MultiUpload.prototype.addDefaultImage = function(serverIds) {
        var re = /^\[(.*)\]$/;
        var fileIds = serverIds;

        if (serverIds instanceof String){
            if (re.test(serverIds)) {
                fileIds = $.parseJSON(serverIds).join(',');
            }
        }
        else if (serverIds instanceof Array) {
            fileIds = serverIds.join(',');
        }

        var that = this;
        $.ajax({
            url: base+'/file/getImageDetails',
            method: 'get',
            data: {serverIds:fileIds},
            dataType: 'json',
            success: function(data) {
                if(data.flag){
                    var imageDetailList = data.data;
                    for (var i=0;i<imageDetailList.length;i++) {
                        var imageDetail = imageDetailList[i];
                        that.uploadedInfos.push({id:null,serverId:imageDetail.serverId,key:imageDetail.key});
                        that.addFile(imageDetail.src,null,imageDetail.serverId);
                    }
                }
                else {
                    showErrorMessage('默认图片加载失败',data.msg);
                }
            },
            error: function() {
                showNetErrorWarning();
            }
        });
    };

    MultiUpload.prototype.addFile = function(src,id,serverId) {
        var that = this;
        var preview = $('<div class="multiupload-preview"></div>');
        var imageWrapper = $('<div class="multiupload-image"></div>');
        var image = $('<img />');
        image.attr('src',src);

        var delBtn = $('<a href="javascript: void(0);"><img src="'+base + '/static/img/delete.png'+'"></a>');
        var delWrapper = $('<div class="multiupload-remove"></div>');
        delWrapper.append(delBtn);

        imageWrapper.append(image);
        preview.append(imageWrapper);
        preview.append(delWrapper);
        this.wrapper.append(preview);
        delBtn.on('click',function(e){
            that.removeFile(id || serverId);
            preview.remove();
            if (that.wrapper.find('.multiupload-preview').length == 0){
                that.message.show();
            }
            e.stopPropagation();
        });
        this.message.hide();
    };

    MultiUpload.prototype.removeFile = function (id) {
        var file = this.uploader.getFile(id);
        if (file){
            this.uploader.removeFile(file);
        }
        for (var i = 0; i < this.uploadedInfos.length; i++) {
            if ((this.uploadedInfos[i].id && id == this.uploadedInfos[i].id) || (this.uploadedInfos[i].serverId && this.uploadedInfos[i].serverId == id)) {
                this.uploadedInfos.splice(i, 1);
                break;
            }
        }
    };

    /**
     * 禁止选择文件
     */
    MultiUpload.prototype.disable = function () {
        this.dropzone.attr('disabled','disabled');
    };

    /**
     * 启用选择文件
     */
    MultiUpload.prototype.enable = function () {
        this.dropzone.removeAttr('disabled');
    };

    /**
     * 文件上传列表是否为空
     * @returns {boolean}
     */
    MultiUpload.prototype.isEmpty = function () {
        return this.uploader.files.length == 0 && this.uploadedInfos.length == 0;
    };

    /**
     * 上传文件
     * @param callback
     */
    MultiUpload.prototype.start = function (callback) {
        $('.preloaderAjax').show();
        this.callback = callback;
        this.disable();
        this.uploader.start();
    };

    global.iCare = {
        radioUpload: RadioUpload,
        multiUpload: MultiUpload
    };

})(window);